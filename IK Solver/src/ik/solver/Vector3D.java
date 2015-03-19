/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik.solver;

/**
 *
 * @author Vitaljok
 * @param <T>
 */
public class Vector3D<T extends Number> implements Cloneable {

    private final T a, b, c;

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public T getC() {
        return c;
    }

    public Vector3D(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector3D<Double> add(Vector3D<?> vec) {
        return new Vector3D(a.doubleValue() + vec.a.doubleValue(),
                b.doubleValue() + vec.b.doubleValue(),
                c.doubleValue() + vec.c.doubleValue());
    }

    public Vector3D<Double> sub(Vector3D<?> vec) {
        return new Vector3D(a.doubleValue() - vec.a.doubleValue(),
                b.doubleValue() - vec.b.doubleValue(),
                c.doubleValue() - vec.c.doubleValue());
    }

    public double dot(Vector3D<?> vec) {
        return (a.doubleValue() * vec.a.doubleValue()
                + b.doubleValue() * vec.b.doubleValue()
                + c.doubleValue() * vec.c.doubleValue());
    }

    public Vector3D<Double> cross(Vector3D<?> vec) {
        Double newA = b.doubleValue() * vec.c.doubleValue() - c.doubleValue() * vec.b.doubleValue();
        Double newB = c.doubleValue() * vec.a.doubleValue() - a.doubleValue() * vec.c.doubleValue();
        Double newC = a.doubleValue() * vec.b.doubleValue() - b.doubleValue() * vec.a.doubleValue();
        return new Vector3D<>(newA, newB, newC);
    }

    public Vector3D<Double> mult(Double value) {
        Double newA = a.doubleValue() * value;
        Double newB = b.doubleValue() * value;
        Double newC = b.doubleValue() * value;
        return new Vector3D<>(newA, newB, newC);
    }

    public Double lenght() {
        return Math.sqrt(a.doubleValue() * a.doubleValue()
                + b.doubleValue() * b.doubleValue()
                + c.doubleValue() * c.doubleValue());
    }

    public double scalTrip(Vector3D<?> vecB, Vector3D<?> vecC) {
        return this.dot(vecB.cross(vecC));
    }

    public Vector3D<Double> vecTrip(Vector3D<?> vecB, Vector3D<?> vecC) {
        return this.cross(vecB.cross(vecC));
    }

    @Override
    public String toString() {
        return "<" + a.toString() + ", " + b.toString() + ", " + c.toString() + ">";
    }

    @Override
    protected Vector3D clone() {
        return new Vector3D<>(a, b, c);
    }
}
