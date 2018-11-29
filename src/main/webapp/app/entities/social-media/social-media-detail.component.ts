import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocialMedia } from 'app/shared/model/social-media.model';

@Component({
    selector: 'jhi-social-media-detail',
    templateUrl: './social-media-detail.component.html'
})
export class SocialMediaDetailComponent implements OnInit {
    socialMedia: ISocialMedia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialMedia }) => {
            this.socialMedia = socialMedia;
        });
    }

    previousState() {
        window.history.back();
    }
}
