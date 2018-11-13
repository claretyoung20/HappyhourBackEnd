import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IContactUs } from 'app/shared/model/contact-us.model';
import { ContactUsService } from './contact-us.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-contact-us-update',
    templateUrl: './contact-us-update.component.html'
})
export class ContactUsUpdateComponent implements OnInit {
    private _contactUs: IContactUs;
    isSaving: boolean;

    restaurants: IRestaurant[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private contactUsService: ContactUsService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactUs }) => {
            this.contactUs = contactUs;
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
        this.contactUs.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        if (this.contactUs.id !== undefined) {
            this.subscribeToSaveResponse(this.contactUsService.update(this.contactUs));
        } else {
            this.subscribeToSaveResponse(this.contactUsService.create(this.contactUs));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContactUs>>) {
        result.subscribe((res: HttpResponse<IContactUs>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get contactUs() {
        return this._contactUs;
    }

    set contactUs(contactUs: IContactUs) {
        this._contactUs = contactUs;
        this.dateCreated = moment(contactUs.dateCreated).format(DATE_TIME_FORMAT);
    }
}
