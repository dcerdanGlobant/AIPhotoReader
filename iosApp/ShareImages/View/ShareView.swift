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
        }
        .interactiveDismissDisabled()
        .onAppear { viewModel.viewAppeared() }
    }
}

// MARK: - Views
private extension ShareView {
    var navigationView: some View {
        HStack(spacing: 0) {
            Button {
                viewModel.dismiss()
            } label: {
                HStack(spacing: 0) {
                    Text("cancel")
                        .font(.body)
                    Spacer()
                }.padding(.leading)
            }.frame(width: UIScreen.main.bounds.width / 3)
            Text("aiphotoreader")
                .font(.headline)
                .frame(width: UIScreen.main.bounds.width / 3)
            Spacer()
                .frame(width: UIScreen.main.bounds.width / 3)
        }
        .padding()
        .background(.ultraThinMaterial)
        .overlay(Divider(), alignment: .bottom)
    }
    
    var contentView: some View {
        ScrollView(.vertical) {
            VStack(alignment: .center, spacing: 20) {
                Image(uiImage: viewModel.image)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: UIScreen.main.bounds.width - 40)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                if let description = viewModel.description {
                    Text(description)
                        .font(.body)
                        .multilineTextAlignment(.center)
                } else {
                    ProgressView()
                        .progressViewStyle(.circular)
                }
                if viewModel.description.isNotNil {
                    Button {
                        viewModel.getMoreInfo()
                    } label: {
                        Text("getmoreinfo")
                            .font(.body)
                    }.buttonStyle(.bordered)
                }
                Spacer()
            }.padding(20)
        }.scrollBounceBehavior(.basedOnSize)
    }
}
