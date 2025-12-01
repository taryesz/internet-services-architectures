import { Routes } from '@angular/router';
import { GenreListComponent } from './components/genre-list/genre-list';
import { GenreFormComponent } from './components/genre-form/genre-form';
import {GenreDetailsComponent} from './components/genre-details/genre-details';
import {MovieFormComponent} from './components/movie-form/movie-form';
import {MovieDetailsComponent} from './components/movie-details/movie-details';

export const routes: Routes = [
  { path: 'genres', component: GenreListComponent },
  { path: 'genres/add', component: GenreFormComponent },
  { path: 'genres/:id/edit', component: GenreFormComponent },
  { path: 'genres/:id', component: GenreDetailsComponent },
  { path: 'genres/:genreId/movies/add', component: MovieFormComponent },
  { path: 'genres/:genreId/movies/:movieId/edit', component: MovieFormComponent },
  { path: 'genres/:genreId/movies/:movieId', component: MovieDetailsComponent },
  { path: '', redirectTo: 'genres', pathMatch: 'full' }
];
