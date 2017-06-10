package me.winter.boing.util;

/**
 * A float computed at access time, letting its value change if elements of his formula changed
 * <p>
 * Created by Alexander Winter on 2017-06-10.
 */
@FunctionalInterface
public interface DynamicFloat
{
	float getValue();
}
