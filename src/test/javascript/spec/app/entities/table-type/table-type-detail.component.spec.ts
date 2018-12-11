/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { TableTypeDetailComponent } from 'app/entities/table-type/table-type-detail.component';
import { TableType } from 'app/shared/model/table-type.model';

describe('Component Tests', () => {
    describe('TableType Management Detail Component', () => {
        let comp: TableTypeDetailComponent;
        let fixture: ComponentFixture<TableTypeDetailComponent>;
        const route = ({ data: of({ tableType: new TableType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [TableTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TableTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TableTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tableType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
