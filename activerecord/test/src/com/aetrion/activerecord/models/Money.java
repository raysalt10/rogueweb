package com.aetrion.activerecord.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing money.
 *
 * @author Anthony Eden
 */
public class Money {

    /**
     * Enumeration of currencies.
     */
    public enum Currency {
        USD("USD"),
        DKK("DKK"),;

        private String s;

        Currency(String s) {
            this.s = s;
        }

        public String toString() {
            return s;
        }
    }

    public static class ExchangeRate {

        private Currency from;
        private Currency to;
        private double rate;

        ExchangeRate(Currency from, Currency to, double rate) {
            this.from = from;
            this.to = to;
            this.rate = rate;
        }

        public Currency getFrom() {
            return from;
        }

        public Currency getTo() {
            return to;
        }

        public double getRate() {
            return rate;
        }
    }

    private static final Map<String, ExchangeRate> EXCHANGE_RATES = new HashMap<String, ExchangeRate>();

    static {
        EXCHANGE_RATES.put("USD_TO_DKK", new ExchangeRate(Currency.USD, Currency.DKK, 6));
        EXCHANGE_RATES.put("DKK_TO_USD", new ExchangeRate(Currency.DKK, Currency.USD, 0.6));
    }

    private Double amount;
    private Currency currency;

    Money(Double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money exchangeTo(Currency otherCurrency) {
        Double newAmount = (amount * EXCHANGE_RATES.get(currency + "_TO_" + otherCurrency).getRate());
        return new Money(Math.floor(newAmount), otherCurrency);
    }
}