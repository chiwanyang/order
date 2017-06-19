import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MenuComponent } from './menu.component';
import { MenuDetailComponent } from './menu-detail.component';
import { MenuPopupComponent } from './menu-dialog.component';
import { MenuDeletePopupComponent } from './menu-delete-dialog.component';

import { Principal } from '../../shared';

export const menuRoute: Routes = [
    {
        path: 'menu',
        component: MenuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.menu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'menu/:id',
        component: MenuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.menu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const menuPopupRoute: Routes = [
    {
        path: 'menu-new',
        component: MenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.menu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menu/:id/edit',
        component: MenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.menu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menu/:id/delete',
        component: MenuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'orderApp.menu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
