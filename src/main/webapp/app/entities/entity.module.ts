import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HappybourBackEndRestaurantModule } from './restaurant/restaurant.module';
import { HappybourBackEndCustomerModule } from './customer/customer.module';
import { HappybourBackEndStaffModule } from './staff/staff.module';
import { HappybourBackEndTimeManagerModule } from './time-manager/time-manager.module';
import { HappybourBackEndSocialMediaModule } from './social-media/social-media.module';
import { HappybourBackEndAddressModule } from './address/address.module';
import { HappybourBackEndCategoryModule } from './category/category.module';
import { HappybourBackEndProductTypeModule } from './product-type/product-type.module';
import { HappybourBackEndProductModule } from './product/product.module';
import { HappybourBackEndProductImageModule } from './product-image/product-image.module';
import { HappybourBackEndCouponModule } from './coupon/coupon.module';
import { HappybourBackEndOrderStatusModule } from './order-status/order-status.module';
import { HappybourBackEndHappyOrderModule } from './happy-order/happy-order.module';
import { HappybourBackEndSaleOrderModule } from './sale-order/sale-order.module';
import { HappybourBackEndDiscountModule } from './discount/discount.module';
import { HappybourBackEndCartModule } from './cart/cart.module';
import { HappybourBackEndContactUsModule } from './contact-us/contact-us.module';
import { HappybourBackEndBookTableModule } from './book-table/book-table.module';
import { HappybourBackEndReservationModule } from './reservation/reservation.module';
import { HappybourBackEndRatingModule } from './rating/rating.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HappybourBackEndRestaurantModule,
        HappybourBackEndCustomerModule,
        HappybourBackEndStaffModule,
        HappybourBackEndTimeManagerModule,
        HappybourBackEndSocialMediaModule,
        HappybourBackEndAddressModule,
        HappybourBackEndCategoryModule,
        HappybourBackEndProductTypeModule,
        HappybourBackEndProductModule,
        HappybourBackEndProductImageModule,
        HappybourBackEndCouponModule,
        HappybourBackEndOrderStatusModule,
        HappybourBackEndHappyOrderModule,
        HappybourBackEndSaleOrderModule,
        HappybourBackEndDiscountModule,
        HappybourBackEndCartModule,
        HappybourBackEndContactUsModule,
        HappybourBackEndBookTableModule,
        HappybourBackEndReservationModule,
        HappybourBackEndRatingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndEntityModule {}
