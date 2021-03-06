package com.cfpamf.test.design.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * 算数表达式求值
 * 直接调用Calculator的类方法conversion()
 * 传入算数表达式，将返回一个浮点值结果
 * 如果计算过程错误，将返回一个NaN
 *
 * @author flavone
 */
public class CalculatorUtil {
    /**
     * 后缀式栈
     */
    private Stack<String> postfixStack = new Stack<>();
    /**
     * 运算符栈
     */
    private Stack<Character> opStack = new Stack<>();

    /**
     * 运用运算符ASCII码-40做索引的运算符优先级
     */
    private int[] opPriority = new int[]{0, 3, 2, 1, -1, 1, 0, 2};

    /**
     * 将表达式转换为结果
     *
     * @param expression
     * @return
     */
    public static BigDecimal conversion(String expression) {
        BigDecimal result;
        CalculatorUtil cal = new CalculatorUtil();
        if (null == expression) {
            return BigDecimal.ZERO;
        }
        expression = PhaseUtil.replaceInvalidSymbol(expression);
        expression = expression.replaceAll(",", "");
        expression = transform(expression);
        result = cal.calculate(expression);
        return result;
    }

    /**
     * 将表达式中负数的符号更改
     *
     * @param expression 例如-2+-1*(-3E-2)-(-1) 被转为 ~2+~1*(~3E~2)-(~1)
     * @return
     */
    private static String transform(String expression) {
        char[] arr = expression.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '-') {
                if (i == 0) {
                    arr[i] = '~';
                } else {
                    char c = arr[i - 1];
                    if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == 'E' || c == 'e') {
                        arr[i] = '~';
                    }
                }
            }
        }
        if (arr[0] == '~' || arr[1] == '(') {
            arr[0] = '-';
            return "0" + new String(arr);
        } else {
            return new String(arr);
        }
    }

    /**
     * 按照给定的表达式计算
     *
     * @param expression 要计算的表达式例如:5+12*(3+5)/7
     * @return
     */
    private BigDecimal calculate(String expression) {
        Stack<String> resultStack = new Stack<>();
        prepare(expression);
        // 将后缀式栈反转
        Collections.reverse(postfixStack);
        // 参与计算的第一个值，第二个值和算术运算符
        String firstValue, secondValue, currentValue;
        while (!postfixStack.isEmpty()) {
            currentValue = postfixStack.pop();
            // 如果不是运算符则存入操作数栈中
            if (!isOperator(currentValue.charAt(0))) {
                currentValue = currentValue.replace("~", "-");
                resultStack.push(currentValue);
            } else {
                // 如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                secondValue = resultStack.pop();
                firstValue = resultStack.pop();

                // 将负数标记符改为负号
                firstValue = firstValue.replace("~", "-");
                secondValue = secondValue.replace("~", "-");

                String tempResult = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }
        return new BigDecimal(resultStack.pop());
    }

    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     *
     * @param expression
     */
    private void prepare(String expression) {
        // 运算符放入栈底元素逗号，此符号优先级最低
        opStack.push(',');
        char[] arr = expression.toCharArray();
        // 当前字符的位置
        int currentIndex = 0;
        // 上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        int count = 0;
        // 当前操作符和栈顶操作符
        char currentOp, peekOp;
        for (int i = 0; i < arr.length; i++) {
            currentOp = arr[i];
            // 如果当前字符是运算符
            if (isOperator(currentOp)) {
                if (count > 0) {
                    // 取两个运算符之间的数字
                    postfixStack.push(new String(arr, currentIndex, count));
                }
                peekOp = opStack.peek();
                // 遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                if (currentOp == ')') {
                    while (opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while (currentOp != '(' && peekOp != ',' && compare(currentOp, peekOp)) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i + 1;
            } else {
                count++;
            }
        }
        // 最后一个字符不是括号或者其他运算符的则加入后缀式栈中
        if (count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {
            postfixStack.push(new String(arr, currentIndex, count));
        }

        while (opStack.peek() != ',') {
            // 将操作符栈中的剩余的元素添加到后缀式栈中
            postfixStack.push(String.valueOf(opStack.pop()));
        }
    }

    /**
     * 判断是否为算术符号
     *
     * @param c
     * @return
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    /**
     * 利用ASCII码-40做下标去算术符号优先级
     *
     * @param cur
     * @param peek
     * @return
     */
    private boolean compare(char cur, char peek) {
        boolean result = false;
        // 如果是peek优先级高于cur，返回true，默认都是peek优先级要低
        if (opPriority[(peek) - 40] >= opPriority[(cur) - 40]) {
            result = true;
        }
        return result;
    }

    /**
     * 按照给定的算术运算符做计算
     *
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private String calculate(String firstValue, String secondValue, char currentOp) {
        String result = "";
        switch (currentOp) {
            case '+':
                result = String.valueOf(AlgorithmHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(AlgorithmHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(AlgorithmHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(AlgorithmHelper.div(firstValue, secondValue));
                break;
            default:
                break;
        }
        return result;
    }

    public static int[] getRandom(Integer count, Integer start, Integer end) {
        if (count > (end - start + 1) || end < start) {
            return null;
        }
        int[] result = new int[count];
        int c = 0;
        while (c < count) {
            int num = (int) (Math.random() * (end - start)) + start;
            boolean flag = true;
            for (int j = 0; j < count; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[c] = num;
                c++;
            }
        }
        Arrays.sort(result);
        return result;
    }
}
