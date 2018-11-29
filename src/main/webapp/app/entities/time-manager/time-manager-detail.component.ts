import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeManager } from 'app/shared/model/time-manager.model';

@Component({
    selector: 'jhi-time-manager-detail',
    templateUrl: './time-manager-detail.component.html'
})
export class TimeManagerDetailComponent implements OnInit {
    timeManager: ITimeManager;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeManager }) => {
            this.timeManager = timeManager;
        });
    }

    previousState() {
        window.history.back();
    }
}
