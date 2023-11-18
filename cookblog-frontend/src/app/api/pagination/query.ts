import { HttpParams } from '@angular/common/http';

export interface Query {
  toHttpParams(): HttpParams;
}
