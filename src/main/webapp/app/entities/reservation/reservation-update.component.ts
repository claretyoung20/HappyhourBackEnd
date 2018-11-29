import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { IStaff } from 'app/shared/model/staff.model';
import { StaffService } from 'app/entities/staff';
import { IBookTable } from 'app/shared/model/book-table.model';
import { BookTableService } from 'app/entities/book-table';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-reservation-update',
    templateUrl: './reservation-update.component.html'
})
export class ReservationUpdateComponent implements OnInit {
    private _reservation: IReservation;
    isSaving: boolean;

    staff: IStaff[];

    booktables: IBookTable[];

    customers: ICustomer[];
    reserverDateDp: any;
    updatedDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reservationService: ReservationService,
        private staffService: StaffService,
        private bookTableService: BookTableService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reservation }) => {
            this.reservation = reservation;
        });
        this.staffService.query().subscribe(
            (res: HttpResponse<IStaff[]>) => {
                this.staff = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bookTableService.query().subscribe(
            (res: HttpResponse<IBookTable[]>) => {
                this.booktables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reservation.id !== undefined) {
            this.subscribeToSaveResponse(this.reservationService.update(this.reservation));
        } else {
            this.subscribeToSaveResponse(this.reservationService.create(this.reservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>) {
        result.subscribe((res: HttpResponse<IReservation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStaffById(index: number, item: IStaff) {
        return item.id;
    }

    trackBookTableById(index: number, item: IBookTable) {
        return item.id;
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
    get reservation() {
        return this._reservation;
    }

    set reservation(reservation: IReservation) {
        this._reservation = reservation;
    }
}
