package com.jpm.booking.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowTest {

    private Show show;

    @BeforeEach
    void instantiate() {
        show = new Show();
    }

    @Test
    void mustHaveShowNumber() {
        show.setShowNumber("202312ABCDE");
        assertThat(show.getShowNumber()).isEqualTo("202312ABCDE");
    }

    @Test
    void mustHaveAbilityToAddSingleSeat() {
        show.getSeats().add(new Seat());
        assertThat(show.getSeats()).hasSize(1);
    }

    @Test
    void mustHaveAbilityToAddMultipleSeats() {
        show.getSeats().addAll(List.of(new Seat(), new Seat()));
        assertThat(show.getSeats()).hasSize(2);
    }
}
