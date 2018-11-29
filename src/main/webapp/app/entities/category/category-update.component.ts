import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';
import { IProductType } from 'app/shared/model/product-type.model';
import { ProductTypeService } from 'app/entities/product-type';

@Component({
    selector: 'jhi-category-update',
    templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
    private _category: ICategory;
    isSaving: boolean;

    restaurants: IRestaurant[];

    producttypes: IProductType[];
    dateCreated: string;
    dateUpdated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private categoryService: CategoryService,
        private restaurantService: RestaurantService,
        private productTypeService: ProductTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productTypeService.query().subscribe(
            (res: HttpResponse<IProductType[]>) => {
                this.producttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.category.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        this.category.dateUpdated = moment(this.dateUpdated, DATE_TIME_FORMAT);
        if (this.category.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryService.update(this.category));
        } else {
            this.subscribeToSaveResponse(this.categoryService.create(this.category));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>) {
        result.subscribe((res: HttpResponse<ICategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }

    trackProductTypeById(index: number, item: IProductType) {
        return item.id;
    }
    get category() {
        return this._category;
    }

    set category(category: ICategory) {
        this._category = category;
        this.dateCreated = moment(category.dateCreated).format(DATE_TIME_FORMAT);
        this.dateUpdated = moment(category.dateUpdated).format(DATE_TIME_FORMAT);
    }
}
