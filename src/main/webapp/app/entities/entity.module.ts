import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HappybourBackEndRestaurantModule } from './restaurant/restaurant.module';
import { HappybourBackEndCustomerModule } from './customer/customer.module';
import { HappybourBackEndStaffModule } from './staff/staff.module';
import { HappybourBackEndTimeManagerModule } from './time-manager/time-manager.module';
import { HappybourBackEndSocialMediaModule } from './social-media/social-media.module';
import { HappybourBackEndAddressModule } from './address/address.module';
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
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndEntityModule {}
