import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrderSharedModule } from '../../shared';
import {
    UserMenuRelService,
    UserMenuRelPopupService,
    UserMenuRelComponent,
    UserMenuRelDetailComponent,
    UserMenuRelDialogComponent,
    UserMenuRelPopupComponent,
    UserMenuRelDeletePopupComponent,
    UserMenuRelDeleteDialogComponent,
    userMenuRelRoute,
    userMenuRelPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userMenuRelRoute,
    ...userMenuRelPopupRoute,
];

@NgModule({
    imports: [
        OrderSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserMenuRelComponent,
        UserMenuRelDetailComponent,
        UserMenuRelDialogComponent,
        UserMenuRelDeleteDialogComponent,
        UserMenuRelPopupComponent,
        UserMenuRelDeletePopupComponent,
    ],
    entryComponents: [
        UserMenuRelComponent,
        UserMenuRelDialogComponent,
        UserMenuRelPopupComponent,
        UserMenuRelDeleteDialogComponent,
        UserMenuRelDeletePopupComponent,
    ],
    providers: [
        UserMenuRelService,
        UserMenuRelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderUserMenuRelModule {}
