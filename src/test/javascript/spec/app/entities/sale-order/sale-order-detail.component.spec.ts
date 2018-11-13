/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { SaleOrderDetailComponent } from 'app/entities/sale-order/sale-order-detail.component';
import { SaleOrder } from 'app/shared/model/sale-order.model';

describe('Component Tests', () => {
    describe('SaleOrder Management Detail Component', () => {
        let comp: SaleOrderDetailComponent;
        let fixture: ComponentFixture<SaleOrderDetailComponent>;
        const route = ({ data: of({ saleOrder: new SaleOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [SaleOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaleOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saleOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
