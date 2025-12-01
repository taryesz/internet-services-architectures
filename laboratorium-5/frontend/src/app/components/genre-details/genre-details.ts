import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { Genre } from '../../models/genre';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-genre-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './genre-details.html',
  styleUrl: './genre-details.css'
})

export class GenreDetailsComponent implements OnInit {

  id: string | null = null;
  genre: Genre | null = null;
  movies: Movie[] = [];

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    if (this.id) {
      this.fetchData();
    }

  }

  fetchData(): void {
    if (!this.id) return;

    this.apiService.getGenre(this.id).subscribe(data => {
      this.genre = data;
      this.cdr.detectChanges();
    });

    this.apiService.getMoviesByGenre(this.id).subscribe(data => {
      this.movies = data;
      this.cdr.detectChanges();
    });
  }

  onDeleteMovie(movieId: string): void {
    if (confirm('Usunac ten film?')) {
      this.apiService.deleteMovie(movieId).subscribe(() => {
        this.fetchData();
      });
    }
  }

}
