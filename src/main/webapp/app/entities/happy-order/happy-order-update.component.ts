import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IHappyOrder } from 'app/shared/model/happy-order.model';
import { HappyOrderService } from './happy-order.service';
import { IOrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from 'app/entities/order-status';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { ICoupon } from 'app/shared/model/coupon.model';
import { CouponService } from 'app/entities/coupon';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';
import { IStaff } from 'app/shared/model/staff.model';
import { StaffService } from 'app/entities/staff';

@Component({
    selector: 'jhi-happy-order-update',
    templateUrl: './happy-order-update.component.html'
})
export class HappyOrderUpdateComponent implements OnInit {
    private _happyOrder: IHappyOrder;
    isSaving: boolean;

    orderstatuses: IOrderStatus[];

    customers: ICustomer[];

    coupons: ICoupon[];

    restaurants: IRestaurant[];

    staff: IStaff[];
    dateCreated: string;
    dateUpdated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private happyOrderService: HappyOrderService,
        private orderStatusService: OrderStatusService,
        private customerService: CustomerService,
        private couponService: CouponService,
        private restaurantService: RestaurantService,
        private staffService: StaffService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ happyOrder }) => {
            this.happyOrder = happyOrder;
        });
        this.orderStatusService.query().subscribe(
            (res: HttpResponse<IOrderStatus[]>) => {
                this.orderstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.couponService.query().subscribe(
            (res: HttpResponse<ICoupon[]>) => {
                this.coupons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.staffService.query().subscribe(
            (res: HttpResponse<IStaff[]>) => {
                this.staff = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.happyOrder.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        this.happyOrder.dateUpdated = moment(this.dateUpdated, DATE_TIME_FORMAT);
        if (this.happyOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.happyOrderService.update(this.happyOrder));
        } else {
            this.subscribeToSaveResponse(this.happyOrderService.create(this.happyOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHappyOrder>>) {
        result.subscribe((res: HttpResponse<IHappyOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrderStatusById(index: number, item: IOrderStatus) {
        return item.id;
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }

    trackCouponById(index: number, item: ICoupon) {
        return item.id;
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }

    trackStaffById(index: number, item: IStaff) {
        return item.id;
    }
    get happyOrder() {
        return this._happyOrder;
    }

    set happyOrder(happyOrder: IHappyOrder) {
        this._happyOrder = happyOrder;
        this.dateCreated = moment(happyOrder.dateCreated).format(DATE_TIME_FORMAT);
        this.dateUpdated = moment(happyOrder.dateUpdated).format(DATE_TIME_FORMAT);
    }
}
