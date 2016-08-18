package com.lonja.calculator.ui.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lonja.calculator.R;
import com.lonja.calculator.databinding.ActivityCalculatorBinding;
import com.lonja.calculator.ui.common.BaseActivity;
import com.lonja.calculator.ui.common.View;
import com.lonja.calculator.ui.common.navigator.ActivityNavigator;

public class CalculatorActivity extends BaseActivity<ActivityCalculatorBinding, CalculatorViewModel>
        implements View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new CalculatorViewModel(new ActivityNavigator(this)));
        setAndBindContentView(R.layout.activity_calculator, savedInstanceState);

        setSupportActionBar(binding.toolbar);

        CalculatorPagerAdapter adapter = new CalculatorPagerAdapter(getSupportFragmentManager());
        PalindromeFragment palindromeFragment = PalindromeFragment.newInstance();
        FactorialFragment factorialFragment = FactorialFragment.newInstance();
        PairsFragment pairsFragment = PairsFragment.newInstance();
        adapter.addFragment(palindromeFragment,
                getString(PalindromeFragment.TITLE));
        adapter.addFragment(factorialFragment,
                getString(FactorialFragment.TITLE));
        adapter.addFragment(pairsFragment,
                getString(PairsFragment.TITLE));

        binding.calculatorViewPager.setAdapter(adapter);
        binding.calculatorTabs.setupWithViewPager(binding.calculatorViewPager);
    }
}
