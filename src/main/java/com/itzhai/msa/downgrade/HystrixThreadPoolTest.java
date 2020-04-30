package com.itzhai.msa.downgrade;

import com.netflix.hystrix.*;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试Hystrix线程池
 *
 * 参考文章：https://www.cnblogs.com/xinzhao/p/11398534.html
 *
 * Created by arthinking on 15/4/2020.
 */
public class HystrixThreadPoolTest {

	private static final int coreSize = 5, maximumSize = 10, maxQueueSize = 10;

	public static void main(String[] args) throws InterruptedException {

		final String commandName = "TestThreadPoolCommand";

		ExecutorService executorService = Executors.newFixedThreadPool(coreSize + maximumSize + maxQueueSize);

		// 配置command
		final HystrixCommand.Setter commandConfig = buildCommandConfig();

		initMetrics(commandConfig);

		final CountDownLatch stopLatch = new CountDownLatch(1);

		// 创建 coreSize + maximumSize + maxQueueSize 个线程，并使用倒计时锁，控制并发执行
		for (int i = 0; i < coreSize + maximumSize + maxQueueSize; i++) {
			final int threadNo = i + 1;
			System.out.println("Started Jobs: " + threadNo);

			Runnable runnable = buildRunnable(commandConfig, stopLatch, threadNo);
			executorService.submit(runnable);

			Thread.sleep(200);

			logThreadPoolStatus();
		}

		stopLatch.countDown();

		executorService.shutdown();
	}

	private static Runnable buildRunnable(HystrixCommand.Setter commandConfig, CountDownLatch stopLatch, int threadNo) {
		return () -> {
					MyHystrixCommand command = new MyHystrixCommand(commandConfig, threadNo) {
						@Override
						protected ResultInfo run() throws Exception {
							stopLatch.await();
							return ResultInfo.builder().isSuccess(true).data("ThreadNo: " + threadNo).build();
						}
					};
					ResultInfo result = command.execute();
					if (result.isSuccess()){
						System.out.println(result);
					} else {
						System.err.println(result);
					}
				};
	}

	/**
	 * Run command once, so we can get metrics
	 *
	 * @param commandConfig
	 * @throws InterruptedException
	 */
	private static void initMetrics(HystrixCommand.Setter commandConfig) throws InterruptedException {
		HystrixCommand<Void> command = new HystrixCommand<Void>(commandConfig) {
			@Override protected Void run() {
				return null;
			}
		};
		command.execute();
		Thread.sleep(100);
	}

	private static HystrixCommand.Setter buildCommandConfig() {
		return HystrixCommand.Setter
					.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestThreadPoolCommand"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("TestThreadPoolCommand"))
					.andCommandPropertiesDefaults(
							HystrixCommandProperties.Setter()
									.withExecutionTimeoutEnabled(false))
					.andThreadPoolPropertiesDefaults(
							HystrixThreadPoolProperties.Setter()
									.withCoreSize(HystrixThreadPoolTest.coreSize)
									.withMaximumSize(HystrixThreadPoolTest.maximumSize)
									.withAllowMaximumSizeToDivergeFromCoreSize(true)
									.withMaxQueueSize(HystrixThreadPoolTest.maxQueueSize)
									.withQueueSizeRejectionThreshold(HystrixThreadPoolTest.maxQueueSize));
	}

	private static void logThreadPoolStatus() {
		for (HystrixThreadPoolMetrics threadPoolMetrics : HystrixThreadPoolMetrics.getInstances()) {
			StringBuilder sb = new StringBuilder()
					.append("========================================\n")
					.append("ThreadPoolKey      : ").append(threadPoolMetrics.getThreadPoolKey().name()).append("\n")
					.append("CurrentPoolSize    : ").append(getProgressBar(coreSize, threadPoolMetrics.getCurrentPoolSize().intValue())).append("\n")
					.append("CurrentActiveCount : ").append(getProgressBar(coreSize, threadPoolMetrics.getCurrentActiveCount().intValue())).append("\n")
					.append("CurrentQueueSize   : ").append(getProgressBar(maxQueueSize, threadPoolMetrics.getCurrentQueueSize().intValue())).append("\n")
					.append("========================================\n")
					;
			System.out.println(sb);
		}
	}

	private static String getProgressBar(int total, int current) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < current; i++) {
			sb.append("●");
		}
		for (int i = 0; i < total - current; i++) {
			sb.append("○");
		}
		return sb.toString();
	}



}

class MyHystrixCommand extends HystrixCommand<ResultInfo> {

	private int threadNo;

	protected MyHystrixCommand(Setter setter, int threadNo) {
		super(setter);
		this.threadNo = threadNo;
	}

	@Override
	protected ResultInfo run() throws Exception {
		throw new RuntimeException("this command always fails");
	}

	@Override
	protected ResultInfo getFallback() {
		return ResultInfo.builder().isSuccess(false).data("ThreadNo: " + threadNo).build();
	}

}

@Data
@Builder
class ResultInfo {

	private boolean isSuccess;

	private Object data;
}