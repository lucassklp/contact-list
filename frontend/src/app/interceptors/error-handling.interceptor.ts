import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { ToastrService } from '../services/toastr.service';

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {

  constructor(private toastr: ToastrService) {}

  intercept(request: HttpRequest<any[]>, next: HttpHandler): Observable<HttpEvent<any[]>> {

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if(error.status == 400){
          const properties: string[] = error.error.map((err: any) => err.property)
          this.toastr.message("Erro de validação no(s) seguinte(s) campo(s): " + properties.join(", "));
        }
        else {
          if(error.error.error){
            this.toastr.message(error.error.error);
          }
          else{
            this.toastr.message("Um erro inesperado aconteceu");
          }
        }
        return throwError(() => error.error);
      })
    );
  }
}
