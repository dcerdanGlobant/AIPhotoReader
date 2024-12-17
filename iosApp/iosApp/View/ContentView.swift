//
//  ContentView.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import SwiftUI
import ComposeApp

// MARK: - ComposeView
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// MARK: - ContentView
struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all)
    }
}
