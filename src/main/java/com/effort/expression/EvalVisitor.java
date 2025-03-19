package com.effort.expression;

import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Object> {

	private Map<String, Object> values;
	private int instanceId;
	private int largestInstanceId;
 
	public EvalVisitor(Map<String, Object> values, int instanceId) {
		this.values = values;
		this.instanceId = instanceId;

		largestInstanceId = 0;

		for (String key : values.keySet()) {
			int start = key.indexOf('[');
			int end = key.indexOf(']');

			if (start != -1 && end != -1) {
				int index = Integer.parseInt(key.substring(start + 1, end));

				if (index > largestInstanceId) {
					largestInstanceId = index;
				}
			}
		}
	}
}
