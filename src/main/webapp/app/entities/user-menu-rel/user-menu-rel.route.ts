import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserMenuRelComponent } from './user-menu-rel.component';
import { UserMenuRelDetailComponent } from './user-menu-rel-detail.component';
import { UserMenuRelPopupComponent } from './user-menu-rel-dialog.component';
import { UserMenuRelDeletePopupComponent } from './user-menu-rel-delete-dialog.component';

import { Principal } from '../../shared';

export const userMenuRelRoute: Routes = [
    {
        path: 'user-menu-rel',
        component: UserMenuRelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.userMenuRel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-menu-rel/:id',
        component: UserMenuRelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.userMenuRel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMenuRelPopupRoute: Routes = [
    {
        path: 'user-menu-rel-new',
        component: UserMenuRelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.userMenuRel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-menu-rel/:id/edit',
        component: UserMenuRelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.userMenuRel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-menu-rel/:id/delete',
        component: UserMenuRelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.userMenuRel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
