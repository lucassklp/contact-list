import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/dialogs/confirm-dialog/confirm-dialog.component';
import { DialogModel } from 'src/app/models/dialog.model';
import { ToastrService } from '../toastr.service';
import { AbstractService } from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractTableService<TModel> {
  length = 0;
  pageSize = 5;
  pageEvent: PageEvent = {
    pageIndex: 0,
    pageSize: this.pageSize,
    length: this.length
  }

  public dataSource: MatTableDataSource<TModel>;
  
  public abstract get displayedColumns(): string[];
  public abstract get pageSizeOptions(): number[];

  constructor(private service: AbstractService<TModel>,
    protected toastr: ToastrService,
    protected router: Router,
    protected dialog: MatDialog) {
    this.dataSource = new MatTableDataSource<TModel>([])
  }

  update(event?: PageEvent){
    if(event){
      this.pageEvent = event;
    }
    this.service.list(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe(result => {
      this.length = result.totalElements
      this.dataSource = new MatTableDataSource(result.content)
    })
  }

  delete(id: number){
    const message = `Você tem certeza que deseja remover esse item?`;
    const dialogData = new DialogModel("Confirmar ação", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      if(dialogResult){
        this.service.delete(id).subscribe(_ => {
          this.toastr.message('Operação executada com sucesso');
          this.update(this.pageEvent);
        });
      }
    });
  }

  abstract edit(id: number): void;
}