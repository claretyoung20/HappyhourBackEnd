/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { BookTableUpdateComponent } from 'app/entities/book-table/book-table-update.component';
import { BookTableService } from 'app/entities/book-table/book-table.service';
import { BookTable } from 'app/shared/model/book-table.model';

describe('Component Tests', () => {
    describe('BookTable Management Update Component', () => {
        let comp: BookTableUpdateComponent;
        let fixture: ComponentFixture<BookTableUpdateComponent>;
        let service: BookTableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [BookTableUpdateComponent]
            })
                .overrideTemplate(BookTableUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookTableUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookTableService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BookTable(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bookTable = entity;
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
                    const entity = new BookTable();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bookTable = entity;
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
