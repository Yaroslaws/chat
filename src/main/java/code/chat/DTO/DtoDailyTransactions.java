package code.chat.DTO;

import code.chat.utils.BigDecimalHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DtoDailyTransactions {


    public static class GroupKey {
        final LocalDate date;
        final String inn;


        public GroupKey(LocalDate date, String inn) {
            this.date = date;
            this.inn = inn;
        }



        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GroupKey groupKey = (GroupKey) o;
            return Objects.equals(date, groupKey.date) && Objects.equals(inn, groupKey.inn);
        }


        @Override
        public int hashCode() {
            return Objects.hash(date, inn);
        }
    }



    LocalDate localDate;
    BigDecimal sum;
    String inn;
    String name;


    public DtoDailyTransactions(LocalDate localDate, BigDecimal sum, String inn, String name) {
        this.localDate = localDate;
        this.sum = sum;
        this.inn = inn;
        this.name = name;
    }


    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public LocalDate getLocalDate() {
        return localDate;
    }



    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }



    public BigDecimal getSum() {
        return sum;
    }



    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }



    public String getInn() {
        return inn;
    }



    public void setInn(String inn) {
        this.inn = inn;
    }



    public GroupKey getGroupKey() {
        return new GroupKey(localDate, inn);
    }



    private static DtoDailyTransactions merge(List<DtoDailyTransactions> daily) {
        DtoDailyTransactions d = daily.get(0);
        if (daily.size() == 1) {
            return d;
        }

        return new DtoDailyTransactions(
                d.getLocalDate(), BigDecimalHelper.sumByField(daily, DtoDailyTransactions::getSum), d.getInn(), d.name);
    }

    public static List<DtoDailyTransactions> mergeAndSort(Stream<DtoDailyTransactions> daily) {
        return daily.collect(Collectors.groupingBy(DtoDailyTransactions::getGroupKey)).values().stream()
                .map(DtoDailyTransactions::merge)
                .sorted(Comparator.comparing(DtoDailyTransactions::getLocalDate).reversed()
                        .thenComparing(DtoDailyTransactions::getInn)
                        .thenComparing(DtoDailyTransactions::getSum))
                .collect(Collectors.toList());

    }
}
