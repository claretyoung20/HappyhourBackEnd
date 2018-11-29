import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBookTable } from 'app/shared/model/book-table.model';
import { BookTableService } from './book-table.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-book-table-update',
    templateUrl: './book-table-update.component.html'
})
export class BookTableUpdateComponent implements OnInit {
    private _bookTable: IBookTable;
    isSaving: boolean;

    restaurants: IRestaurant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bookTableService: BookTableService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bookTable }) => {
            this.bookTable = bookTable;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bookTable.id !== undefined) {
            this.subscribeToSaveResponse(this.bookTableService.update(this.bookTable));
        } else {
            this.subscribeToSaveResponse(this.bookTableService.create(this.bookTable));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBookTable>>) {
        result.subscribe((res: HttpResponse<IBookTable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get bookTable() {
        return this._bookTable;
    }

    set bookTable(bookTable: IBookTable) {
        this._bookTable = bookTable;
    }
}
