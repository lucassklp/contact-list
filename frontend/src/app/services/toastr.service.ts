import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ToastrService {

  constructor(private _snackBar: MatSnackBar) { }

  message(error: string) {
    this._snackBar.open(error, "Fechar")
  }

}
