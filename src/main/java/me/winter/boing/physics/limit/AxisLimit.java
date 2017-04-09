package me.winter.boing.physics.limit;

import me.winter.boing.physics.IntVector;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Axis-based implementation of the Limit system.
 * Lets you create Limits that are axis aligned to each other. (No limit can be 45 degrees)
 * <p>
 * Created by Alexander Winter on 2016-10-30.
 */
public class AxisLimit implements Limit
{
	/**
	 * Axis this limit if blocking on.
	 * e.g. Y means blocking a vertical movement
	 */
	private final Axis axis;

	/**
	 * Limits can block toward positive value or toward negative
	 * A limit that is forward will block from lowest value toward highest
	 * For example, a Y forward limit will block things coming from bottom
	 */
	private final boolean forward;

	private LimitHolder holder;

	private final IntVector origin; //Relative position of the limit
	private final IntVector size; //Size of the limit

	private final IntVector tmpStart = new IntVector(), tmpEnd = new IntVector();
	private final IntVector tmpVector = new IntVector(), tmpVector2 = new IntVector();

	public AxisLimit(LimitHolder holder, Axis axis, boolean forward, int x, int y, int size)
	{
		this.axis = axis;
		this.forward = forward;
		this.origin = new IntVector(x, y);
		this.size = new IntVector();
		this.setHolder(holder);

		for(Axis current : Axis.values()) //for all axes
			if(current != this.axis) //but the one of this limit
				current.set(this.size, size); //This means in 3D a Limit blocking Y is size units wide on X and Z

		if(axis.of(start()) != axis.of(end())) //If somehow you manage to make your limit non axis aligned
			throw new IllegalArgumentException("Vectors should have the same value for the specified axis");
	}

	public AxisLimit(AxisLimit limit)
	{
		this.axis = limit.axis;
		this.forward = limit.forward;
		this.origin = limit.origin.clone();
		this.size = limit.size.clone();
		this.setHolder(limit.holder);
	}

	@Override
	public boolean collides(Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return false;

		int dir = forward ? -1 : 1;

		IntVector thisVec = getHolder().getMovement();
		IntVector thatVec = that.getHolder().getMovement();

		int thisPos = axisValue();
		int thatPos = that.axisValue();

		if(!(thisPos * dir <= thatPos * dir
				&& (thisPos + axis.of(thisVec)) * dir > (thatPos + axis.of(thatVec)) * dir))
			return false;

		int diff = thatPos - thisPos;

		//finding the collision point
		tmpVector.set(thisVec).scale(diff).divide(axis.of(thisVec) - axis.of(thatVec));
		tmpVector2.set(thatVec).scale(diff).divide(axis.of(thisVec) - axis.of(thatVec));

		if(contains(that, tmpVector, tmpVector2)) //and it was in bounds at the impact point
			return true;

		//finding the collision point + 1 (to prevent the corner glitch)
		tmpVector.scale(diff + dir).divide(diff);
		tmpVector2.scale(diff + dir).divide(diff);

		return contains(that, tmpVector, tmpVector2);
	}

	private boolean contains(AxisLimit limit, IntVector thisOffset, IntVector otherOffset)
	{
		for(Axis axis : Axis.values()) //for all axes
		{
			if(this.axis == axis) //but the one of this limit
				continue;

			if(axis.of(limit.end()) + axis.of(otherOffset) <= axis.of(start()) + axis.of(thisOffset)
			|| axis.of(limit.start()) + axis.of(otherOffset) >= axis.of(end()) + axis.of(thisOffset))
				return false;
		}

		return true;
	}

	@Override
	public float getPriority(Limit limit, IntVector vector)
	{
		if(!(limit instanceof AxisLimit))
			return -1;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return -1;

		return (axis.of(start()) - axis.of(that.start())) / (float)axis.of(vector); //scale his allowed movement from this limit to his total movement on the axis (x / x is same as len / len)
	}

	@Override
	public boolean replace(Limit limit, IntVector result)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return false;

		int dir = forward ? 1 : -1;

		if(axis.of(result) * dir > 0) //if the movement is toward the direction of the limit
		{
			axis.set(result, axisValue() - that.axisValue()); //replace to limit
			return true;
		}

		return false;
	}

	@Override
	public boolean touches(Limit limit)
	{
		if(!(limit instanceof AxisLimit))
			return false;

		AxisLimit that = (AxisLimit)limit;

		if(that.axis != axis || that.forward == forward)
			return false;

		return that.axisValue() == axisValue() && contains(that, IntVector.ZERO, IntVector.ZERO); //same axis value and in bounds
	}

	@Override
	public float surfaceContact(Limit limit) //TODO rename this
	{
		for(Axis axis : Axis.values()) //for all axes
		{
			if(axis == this.axis) //but the one of this limit
				continue;

			float thisStart = axis.of(start());
			float thisEnd = axis.of(end());

			float otherStart = axis.of(limit.start());
			float otherEnd = axis.of(limit.end());

			if(otherEnd > thisStart && otherEnd < thisEnd)
				return min((otherEnd - thisStart) / abs(otherEnd - otherStart), 1);

			else if(otherStart < thisEnd && otherStart > thisStart)
				return min((thisEnd - otherStart) / abs(otherEnd - otherStart), 1);

			else if(otherStart <= thisStart && thisEnd >= thisEnd)
				return min((thisEnd - thisStart) / abs(otherEnd - otherStart), 1);
		}

		return 0;
	}

	@Override
	public boolean equals(Object object)
	{
		if(object.getClass() != getClass())
			return false;

		if(((AxisLimit)object).getAxis() != getAxis())
			return false;

		if(!((AxisLimit)object).getOrigin().equals(getOrigin()))
			return false;

		if(!((AxisLimit)object).getSize().equals(getSize()))
			return false;

		if(((AxisLimit)object).forward != forward)
			return false;

		return true;
	}

	@Override
	public AxisLimit clone()
	{
		return new AxisLimit(this);
	}

	@Override
	public float getAngle()
	{
		return (axis == Axis.X ? 0 : 90) + (forward ? 180 : 0);
	}

	@Override
	public LimitHolder getHolder()
	{
		return this.holder;
	}

	@Override
	public void setHolder(LimitHolder holder)
	{
		this.holder = holder;
	}

	@Override
	public IntVector start()
	{
		return tmpStart.set(origin).add(holder.getPosition());
	}

	@Override
	public IntVector end()
	{
		return tmpEnd.set(origin).add(size).add(holder.getPosition());
	}

	public IntVector getOrigin()
	{
		return origin;
	}

	public IntVector getSize()
	{
		return size;
	}

	public Axis getAxis()
	{
		return axis;
	}

	/**
	 * Gives the x, y, or z coordinate of the limit depending of the axis of this limit
	 * If this is a Y limit, this value corresponds to the height etc.
	 * @return the value for the axis
	 */
	private int axisValue()
	{
		return axis.of(origin) + axis.of(holder.getPosition());
	}

	@Override
	public String toString()
	{
		return start() + " - " + end() + " Angle: " + getAngle();
	}

}
