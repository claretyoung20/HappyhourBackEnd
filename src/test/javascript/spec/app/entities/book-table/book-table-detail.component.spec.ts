/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { BookTableDetailComponent } from 'app/entities/book-table/book-table-detail.component';
import { BookTable } from 'app/shared/model/book-table.model';

describe('Component Tests', () => {
    describe('BookTable Management Detail Component', () => {
        let comp: BookTableDetailComponent;
        let fixture: ComponentFixture<BookTableDetailComponent>;
        const route = ({ data: of({ bookTable: new BookTable(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [BookTableDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BookTableDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookTableDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bookTable).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
