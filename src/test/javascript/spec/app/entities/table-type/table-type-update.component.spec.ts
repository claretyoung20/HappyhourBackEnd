/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { TableTypeUpdateComponent } from 'app/entities/table-type/table-type-update.component';
import { TableTypeService } from 'app/entities/table-type/table-type.service';
import { TableType } from 'app/shared/model/table-type.model';

describe('Component Tests', () => {
    describe('TableType Management Update Component', () => {
        let comp: TableTypeUpdateComponent;
        let fixture: ComponentFixture<TableTypeUpdateComponent>;
        let service: TableTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [TableTypeUpdateComponent]
            })
                .overrideTemplate(TableTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TableTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TableTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TableType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tableType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TableType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tableType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
