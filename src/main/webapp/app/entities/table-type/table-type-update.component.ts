import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITableType } from 'app/shared/model/table-type.model';
import { TableTypeService } from './table-type.service';

@Component({
    selector: 'jhi-table-type-update',
    templateUrl: './table-type-update.component.html'
})
export class TableTypeUpdateComponent implements OnInit {
    private _tableType: ITableType;
    isSaving: boolean;

    constructor(private tableTypeService: TableTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tableType }) => {
            this.tableType = tableType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tableType.id !== undefined) {
            this.subscribeToSaveResponse(this.tableTypeService.update(this.tableType));
        } else {
            this.subscribeToSaveResponse(this.tableTypeService.create(this.tableType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITableType>>) {
        result.subscribe((res: HttpResponse<ITableType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tableType() {
        return this._tableType;
    }

    set tableType(tableType: ITableType) {
        this._tableType = tableType;
    }
}
