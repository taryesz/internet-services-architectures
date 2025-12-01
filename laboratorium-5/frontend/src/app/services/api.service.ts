import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre } from '../models/genre';
import { Movie } from '../models/movie';

@Injectable({
  providedIn: 'root'
})

export class ApiService {

  private readonly baseUrl = 'http://localhost:8080/api/genres';
  private readonly moviesUrl = 'http://localhost:8080/api/movies';

  constructor(private http: HttpClient) { }

  getGenre(id: string): Observable<Genre> {
      return this.http.get<Genre>(`${this.baseUrl}/${id}`)
  }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.baseUrl);
  }

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  createGenre(name: string, actionIndex: number): Observable<any> {

    const id = crypto.randomUUID();

    const newGenre = {
      id: id,
      name: name,
      actionIndex: actionIndex
    };

    return this.http.post(this.baseUrl, newGenre);

  }

  updateGenre(id: string, name: string, actionIndex: number): Observable<any> {
    return this.http.patch(`${this.baseUrl}/${id}`, {
      id: id,
      name: name,
      actionIndex: actionIndex
    });
  }

  getMoviesByGenre(genreId: string): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.moviesUrl}?genreId=${genreId}`);
  }

  deleteMovie(id: string): Observable<void> {
    return this.http.delete<void>(`${this.moviesUrl}/${id}`);
  }

  createMovie(title: string, rating: number, pegi: boolean, genreId: string): Observable<any> {
    const newMovie = {
      id: crypto.randomUUID(),
      title: title,
      rating: rating,
      pegi: pegi,
      genreId: genreId
    };

    return this.http.post(this.moviesUrl, newMovie);
  }

  getMovie(id: string): Observable<Movie> {
    return this.http.get<Movie>(`${this.moviesUrl}/${id}`);
  }

  updateMovie(id: string, title: string, rating: number, pegi: boolean, genreId: string): Observable<any> {
    return this.http.patch(`${this.moviesUrl}/${id}`, {
      id: id,
      title: title,
      rating: rating,
      pegi: pegi,
      genreId: genreId
    });
  }

}
