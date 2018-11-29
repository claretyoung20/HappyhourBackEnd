/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { HappyOrderUpdateComponent } from 'app/entities/happy-order/happy-order-update.component';
import { HappyOrderService } from 'app/entities/happy-order/happy-order.service';
import { HappyOrder } from 'app/shared/model/happy-order.model';

describe('Component Tests', () => {
    describe('HappyOrder Management Update Component', () => {
        let comp: HappyOrderUpdateComponent;
        let fixture: ComponentFixture<HappyOrderUpdateComponent>;
        let service: HappyOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [HappyOrderUpdateComponent]
            })
                .overrideTemplate(HappyOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HappyOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HappyOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HappyOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.happyOrder = entity;
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
                    const entity = new HappyOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.happyOrder = entity;
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
