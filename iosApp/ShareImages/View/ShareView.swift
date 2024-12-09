//
//  ShareView.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

// MARK: - ShareView
struct ShareView: View {
    @StateObject private var viewModel: ShareViewModel
    
    init(with viewModel: ShareViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    // MARK: - View
    var body: some View {
        VStack(spacing: 0) {
            navigationView
            contentView
        }.onAppear {
            viewModel.loadImage()
        }
    }
}

// MARK: - Views
private extension ShareView {
    var navigationView: some View {
        HStack(spacing: 0) {
            Spacer()
                .frame(width: UIScreen.main.bounds.width / 3)
            Text(verbatim: "AiPhotoHeader")
                .font(.headline)
                .frame(width: UIScreen.main.bounds.width / 3)
            Button {
                viewModel.dismiss()
            } label: {
                HStack(spacing: 0) {
                    Spacer()
                    Text(verbatim: "Close")
                }.padding(.trailing)
            }.frame(width: UIScreen.main.bounds.width / 3)
        }
        .padding()
        .background(Color(.systemGray6))
        .overlay(Divider(), alignment: .bottom)
    }
    
    var contentView: some View {
        VStack(alignment: .center, spacing: 20) {
            Image(uiImage: viewModel.image)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: UIScreen.main.bounds.width - 40)
            Button {
                viewModel.getMoreInfo()
            } label: {
                Text(verbatim: "Get More Info")
            }.buttonStyle(.bordered)
            Spacer()
        }.padding(20)
    }
}
