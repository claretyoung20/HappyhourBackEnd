import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';

@Component({
    selector: 'jhi-rating-update',
    templateUrl: './rating-update.component.html'
})
export class RatingUpdateComponent implements OnInit {
    private _rating: IRating;
    isSaving: boolean;
    dateCreated: string;
    dateUpdated: string;

    constructor(private ratingService: RatingService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rating }) => {
            this.rating = rating;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.rating.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        this.rating.dateUpdated = moment(this.dateUpdated, DATE_TIME_FORMAT);
        if (this.rating.id !== undefined) {
            this.subscribeToSaveResponse(this.ratingService.update(this.rating));
        } else {
            this.subscribeToSaveResponse(this.ratingService.create(this.rating));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRating>>) {
        result.subscribe((res: HttpResponse<IRating>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get rating() {
        return this._rating;
    }

    set rating(rating: IRating) {
        this._rating = rating;
        this.dateCreated = moment(rating.dateCreated).format(DATE_TIME_FORMAT);
        this.dateUpdated = moment(rating.dateUpdated).format(DATE_TIME_FORMAT);
    }
}
