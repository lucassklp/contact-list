import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../models/page';

export class AbstractService<TModel> {

  constructor(private name: string, protected http: HttpClient) { }

  public get(id: number): Observable<TModel> {
    return this.http.get<TModel>(`/api/${this.name}/${id}`)
  }

  public save(template: TModel): Observable<void>{
    return this.http.post<void>(`/api/${this.name}`, template);
  }

  public list(offset: number, size: number): Observable<Page<TModel>> {
    return this.http.get<Page<TModel>>(`/api/${this.name}`, {
      params: {
        "page": offset.toString(), 
        "size": size.toString()
      }
    });
  }

  public delete(id: number): Observable<void>{
    return this.http.delete<void>(`/api/${this.name}/${id}`);
  }
}