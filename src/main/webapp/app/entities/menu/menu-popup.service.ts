import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Menu } from './menu.model';
import { MenuService } from './menu.service';
@Injectable()
export class MenuPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private menuService: MenuService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.menuService.find(id).subscribe((menu) => {
                menu.gmt_create = this.datePipe
                    .transform(menu.gmt_create, 'yyyy-MM-ddThh:mm');
                menu.gmt_modified = this.datePipe
                    .transform(menu.gmt_modified, 'yyyy-MM-ddThh:mm');
                this.menuModalRef(component, menu);
            });
        } else {
            return this.menuModalRef(component, new Menu());
        }
    }

    menuModalRef(component: Component, menu: Menu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.menu = menu;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
