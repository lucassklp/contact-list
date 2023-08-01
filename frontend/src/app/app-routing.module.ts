import { Inject, NgModule, inject } from '@angular/core';
import { ActivatedRouteSnapshot, RouterModule, RouterStateSnapshot, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { CreateContactComponent } from './pages/create-contact/create-contact.component';
import { UpdateContactComponent } from './pages/update-contact/update-contact.component';
import { AuthenticationGuard } from './guards/auth.guard';
import { TemplateComponent } from './pages/template/template.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: TemplateComponent,
    children: [
      {
        path: 'contacts',
        component: ContactsComponent,
      },
      {
        path: 'contacts/create',
        component: CreateContactComponent,
      },
      {
        path: 'contacts/:id/update',
        component: UpdateContactComponent,
      }
    ],
    canActivate: [ (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(AuthenticationGuard).canActivate(next, state) ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
