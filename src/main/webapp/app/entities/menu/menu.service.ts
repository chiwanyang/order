import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Menu } from './menu.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MenuService {

    private resourceUrl = 'api/menus';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(menu: Menu): Observable<Menu> {
        const copy = this.convert(menu);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(menu: Menu): Observable<Menu> {
        const copy = this.convert(menu);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Menu> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.gmt_create = this.dateUtils
            .convertDateTimeFromServer(entity.gmt_create);
        entity.gmt_modified = this.dateUtils
            .convertDateTimeFromServer(entity.gmt_modified);
    }

    private convert(menu: Menu): Menu {
        const copy: Menu = Object.assign({}, menu);

        copy.gmt_create = this.dateUtils.toDate(menu.gmt_create);

        copy.gmt_modified = this.dateUtils.toDate(menu.gmt_modified);
        return copy;
    }
}
