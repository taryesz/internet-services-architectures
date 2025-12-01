import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { Genre } from '../../models/genre';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-genre-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './genre-list.html',
  styleUrl: './genre-list.css'
})

export class GenreListComponent implements OnInit {

  genres: Genre[] = [];

  constructor(
    private apiService: ApiService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.fetchGenres();
  }

  fetchGenres(): void {
    this.apiService.getGenres().subscribe({
      next: (data) => {
        this.genres = data;
        this.cdr.detectChanges();
        console.log('Pobrano gatunki:', data);
      },
      error: (err) => console.error('Blad pobierania:', err)
    });
  }

  onDelete(id: string): void {
    if(confirm('Usunac ten gatunek?')) {
      this.apiService.deleteGenre(id).subscribe(() => {
        this.fetchGenres();
      });
    }
  }

}
