// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.searchlib.expression;

/**
 * This function is an instruction to negate its argument.
 *
 * @author baldersheim
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen</a>
 */
public class NegateFunctionNode extends UnaryFunctionNode {

    public static final int classId = registerClass(0x4000 + 60, NegateFunctionNode.class);

    /**
     * Constructs an empty result node. <b>NOTE:</b> This instance is broken until non-optional member data is set.
     */
    public NegateFunctionNode() {

    }

    /**
     * Constructs an instance of this class with given argument.
     *
     * @param arg The argument for this function.
     */
    public NegateFunctionNode(ExpressionNode arg) {
        addArg(arg);
    }

    @Override
    public void onPrepare() {
        super.onPrepare();
    }

    @Override
    public boolean onExecute() {
        getArg().execute();
        getResult().set(getArg().getResult());
        getResult().negate();
        return true;
    }

    @Override
    protected int onGetClassId() {
        return classId;
    }

    @Override
    protected boolean equalsUnaryFunction(UnaryFunctionNode obj) {
        return true;
    }
}
