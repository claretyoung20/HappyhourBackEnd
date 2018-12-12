import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITableType } from 'app/shared/model/table-type.model';

@Component({
    selector: 'jhi-table-type-detail',
    templateUrl: './table-type-detail.component.html'
})
export class TableTypeDetailComponent implements OnInit {
    tableType: ITableType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tableType }) => {
            this.tableType = tableType;
        });
    }

    previousState() {
        window.history.back();
    }
}
