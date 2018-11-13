/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { SaleOrderUpdateComponent } from 'app/entities/sale-order/sale-order-update.component';
import { SaleOrderService } from 'app/entities/sale-order/sale-order.service';
import { SaleOrder } from 'app/shared/model/sale-order.model';

describe('Component Tests', () => {
    describe('SaleOrder Management Update Component', () => {
        let comp: SaleOrderUpdateComponent;
        let fixture: ComponentFixture<SaleOrderUpdateComponent>;
        let service: SaleOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [SaleOrderUpdateComponent]
            })
                .overrideTemplate(SaleOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaleOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SaleOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleOrder = entity;
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
                    const entity = new SaleOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleOrder = entity;
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
