/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.uiautomator.examples.calc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;


public class CalcActivity extends ActionBarActivity {

    private TextView mResult;

    private Long mPreviousValue;
    private Operation mPendingOperation;

    private enum Operation {
        PLUS {
            @Override
            public long resolve(long lhs, long rhs) {
                return lhs + rhs;
            }
        },
        MINUS {
            @Override
            public long resolve(long lhs, long rhs) {
                return lhs - rhs;
            }
        },
        MULTIPLY {
            @Override
            public long resolve(long lhs, long rhs) {
                return lhs * rhs;
            }
        },
        DIVIDE {
            @Override
            public long resolve(long lhs, long rhs) {
                return lhs * rhs;
            }
        },
        EQUALS {
            @Override
            public long resolve(long lhs, long rhs) {
                return rhs;
            }
        };

        public abstract long resolve(long lhs, long rhs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_activity);

        mResult = (TextView)findViewById(R.id.result);
    }

    private long getCurrentValue() {
        return Long.parseLong(mResult.getText().toString());
    }

    private void resolvePendingOperation() {
        // Update result
        long result = mPendingOperation.resolve(mPreviousValue, getCurrentValue());
        mResult.setText(Long.toString(result));

        // Clear pending operations
        mPreviousValue = null;
        mPendingOperation = null;
    }

    public void onClearClick(View v) {
        // If the current value has already been cleared, clear the previous value as well
        if (getCurrentValue() == 0) {
            mPreviousValue = null;
        }
        // Clear the current value
        mResult.setText("0");
    }

    public void onOperationClick(View v) {
        // If we already have a pending operation, we must resolve it first
        if (mPendingOperation != null && mPreviousValue != null) {
            resolvePendingOperation();
        }

        // Save the operation for later (after we collect the rhs)
        TextView view = (TextView)v;
        switch (view.getText().toString()) {
            case "+":
                mPendingOperation = Operation.PLUS;
                break;
            case "-":
                mPendingOperation = Operation.MINUS;
                break;

            case "%":
                mPendingOperation = Operation.DIVIDE;
                break;

            case "*":
                mPendingOperation = Operation.MULTIPLY;
                break;
            case "=":
                mPendingOperation = Operation.EQUALS;
                break;
        }
    }

    public void onNumberClick(View v) {
        long currentValue;
        // If an operation button has been pressed, and we have not yet saved the current value
        if (mPendingOperation != null && mPreviousValue == null) {
            // Save the current value for later
            mPreviousValue = getCurrentValue();
            currentValue = 0;
        } else {
            currentValue = getCurrentValue();
        }

        // Shift the current value and add the new digit
        TextView view = (TextView)v;
        long newValue = currentValue * 10 + Long.parseLong(view.getText().toString());

        // Commit the updated result to the UI
        mResult.setText(Long.toString(newValue));
    }
}
