package com.geoffreymoller.vision.days.service.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public enum VisionDependencyKey implements HystrixCommandGroupKey {
    GET_DAY, POST_DAY, UPDATE_DAY
}