import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
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
import { IBookTable } from 'app/shared/model/book-table.model';
import { BookTableService } from 'app/entities/book-table';

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

    booktables: IBookTable[];
    dateUpdatedDp: any;
    dateCreatedDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private happyOrderService: HappyOrderService,
        private orderStatusService: OrderStatusService,
        private customerService: CustomerService,
        private couponService: CouponService,
        private restaurantService: RestaurantService,
        private staffService: StaffService,
        private bookTableService: BookTableService,
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
        this.bookTableService.query().subscribe(
            (res: HttpResponse<IBookTable[]>) => {
                this.booktables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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

    trackBookTableById(index: number, item: IBookTable) {
        return item.id;
    }
    get happyOrder() {
        return this._happyOrder;
    }

    set happyOrder(happyOrder: IHappyOrder) {
        this._happyOrder = happyOrder;
    }
}
