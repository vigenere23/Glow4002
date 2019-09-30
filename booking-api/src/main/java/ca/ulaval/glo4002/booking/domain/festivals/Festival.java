package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.OffsetDateTime;

public abstract class Festival {

    protected OffsetDateTime startDate;
    protected OffsetDateTime endDate;
    protected OffsetDateTime saleStartDate;
    protected OffsetDateTime saleEndDate;

    protected Festival(OffsetDateTime startDate, OffsetDateTime endDate, OffsetDateTime saleStartDate, OffsetDateTime saleEndDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
    }

    public OffsetDateTime getStartDate() {
        return this.startDate;
    }

    public OffsetDateTime getEndDate() {
        return this.endDate;
    }

    public boolean isDuringSaleTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.saleStartDate) && dateTime.isBefore(this.saleEndDate);
    }

    public boolean isDuringEventTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.startDate) && dateTime.isBefore(this.endDate);
    }
}
