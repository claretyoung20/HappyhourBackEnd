/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { HappyOrderDetailComponent } from 'app/entities/happy-order/happy-order-detail.component';
import { HappyOrder } from 'app/shared/model/happy-order.model';

describe('Component Tests', () => {
    describe('HappyOrder Management Detail Component', () => {
        let comp: HappyOrderDetailComponent;
        let fixture: ComponentFixture<HappyOrderDetailComponent>;
        const route = ({ data: of({ happyOrder: new HappyOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [HappyOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HappyOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HappyOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.happyOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
