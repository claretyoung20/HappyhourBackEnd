import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactUs } from 'app/shared/model/contact-us.model';

@Component({
    selector: 'jhi-contact-us-detail',
    templateUrl: './contact-us-detail.component.html'
})
export class ContactUsDetailComponent implements OnInit {
    contactUs: IContactUs;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactUs }) => {
            this.contactUs = contactUs;
        });
    }

    previousState() {
        window.history.back();
    }
}
