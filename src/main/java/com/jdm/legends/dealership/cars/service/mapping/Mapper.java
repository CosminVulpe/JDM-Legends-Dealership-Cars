package com.jdm.legends.dealership.cars.service.mapping;

@FunctionalInterface
public interface Mapper<T1,T2>  {
    T2 map(T1 source);
}
